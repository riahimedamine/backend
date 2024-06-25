import { Component, OnInit } from '@angular/core';
import { CongeStatisticService } from '../service/conge-statistic.service';
import { ICongeStatistic } from '../conge-statistic.model';
import Chart from 'chart.js/auto';

@Component({
  selector: 'jhi-conge-statistic',
  templateUrl: './conge-statistic.component.html'
})
export class CongeStatisticComponent implements OnInit {

  currentStatusChart: any;
  currentTypesChart: any;
  charts: Map<string, {
    statusChart: any,
    typesChart: any
  }> = new Map();

  currentStatistics: ICongeStatistic[] = [];
  statistics: ICongeStatistic[] = [];
  isLoading = false;

  constructor(private congeStatisticService: CongeStatisticService) {
  }

  ngOnInit(): void {
    this.loadAll();
    this.isLoading = false;
  }

  public refresh(): void {
    this.congeStatisticService.refresh().subscribe(
      () => {
        this.isLoading = true;
        this.destroyCharts();
        this.loadAll();
        this.statistics.forEach((statistic) => {
          this.drawChart(statistic);
        });
        this.isLoading = false;
      }
    );
  }

  drawCurrentCharts(): void {
    const totalDemandes = this.currentStatistics[0].totalDemandes;
    const totalWaitingDemandes = totalDemandes - this.currentStatistics[0].totalAcceptedDemandes - this.currentStatistics[0].totalRefusedDemandes;
    const totalRefusedDemandes = this.currentStatistics[0].totalRefusedDemandes;
    const totalAcceptedDemandes = this.currentStatistics[0].totalAcceptedDemandes;

    const statusesLabels = [
      'Demandes refusées',
      'Demandes acceptées',
      'Demandes en attentes',
      'Demandes totales'];
    const statusesData = [totalRefusedDemandes, totalAcceptedDemandes, totalWaitingDemandes];
    const statusesBackgroundColors = [
      'rgba(255,99,99,0.5)',
      'rgba(75,192,108,0.5)',
      'rgba(86,216,255,0.5)'
    ];

    this.currentStatusChart = new Chart('statuses-current', {
      type: 'doughnut',

      data: {
        labels: statusesLabels,
        datasets: [
          {
            label: 'Demandes de congés',
            data: statusesData,
            backgroundColor: statusesBackgroundColors
          },
          {
            label: 'Demandes de congés',
            data: [0, 0, 0, totalDemandes],
            backgroundColor: 'rgba(236,60,255,0.5)'
          }
        ]
      }
    });

    const types = Object.keys(this.currentStatistics[0].typesWithCounts);
    const counts = Object.values(this.currentStatistics[0].typesWithCounts);

    const typesBackgroundColors = types.map(() => this.random_rgba());

    this.currentTypesChart = new Chart('types-current', {
      type: 'bar',
      data: {
        labels: types,
        datasets: [
          {
            label: 'Demandes de congés par type',
            data: counts,
            backgroundColor: typesBackgroundColors,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }
        ]
      }
    });
  }

  drawChart(statistic: ICongeStatistic): void {

    const year = statistic.year;
    const month = statistic.month;

    const totalDemandes = statistic.totalDemandes;
    const totalWaitingDemandes = totalDemandes - statistic.totalAcceptedDemandes - statistic.totalRefusedDemandes;
    const totalRefusedDemandes = statistic.totalRefusedDemandes;
    const totalAcceptedDemandes = statistic.totalAcceptedDemandes;

    const statusesLabels = [
      'Demandes refusées',
      'Demandes acceptées',
      'Demandes en attentes',
      'Demandes totales'];
    const statusesData = [totalRefusedDemandes, totalAcceptedDemandes, totalWaitingDemandes];
    const statusesBackgroundColors = [
      'rgba(255,99,99,0.5)',
      'rgba(75,192,108,0.5)',
      'rgba(86,216,255,0.5)',
      'rgba(236,60,255,0.5)'
    ];

    const statusChart = new Chart(`statuses-${year}-${month}`, {
      type: 'doughnut',

      data: {
        labels: statusesLabels,
        datasets: [
          {
            label: 'Demandes de congés',
            data: statusesData,
            backgroundColor: statusesBackgroundColors
          },
          {
            label: 'Demandes de congés',
            data: [0, 0, 0, totalDemandes],
            backgroundColor: 'rgba(236,60,255,0.5)'
          }
        ]
      }
    });

    const types = Object.keys(statistic.typesWithCounts);
    const counts = Object.values(statistic.typesWithCounts);

    const typesBackgroundColors = types.map(() => this.random_rgba());

    const typesChart = new Chart(`types-${year}-${month}`, {
      type: 'bar',
      data: {
        labels: types,
        datasets: [
          {
            label: 'Demandes de congés par type',
            data: counts,
            backgroundColor: typesBackgroundColors,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }
        ]
      }
    });

    this.charts.set(`${year}-${month}`, {
      statusChart,
      typesChart
    });
  }

  loadAll(): void {
    this.isLoading = true;
    const year = new Date().getFullYear();
    const month = new Date().getMonth() + 1;
    this.congeStatisticService.loadByYearAndMonth(year, month).subscribe((statistics) => {
      this.currentStatistics = statistics;
      this.drawCurrentCharts();
    });
  };

  destroyCharts(): void {
    this.currentStatusChart.destroy();
    this.currentTypesChart.destroy();
    this.charts.forEach((chart, key) => {
      console.log('Destroying chart for key: ' + key);
      chart.statusChart.destroy();
      chart.typesChart.destroy();
    });
  }

  private random_rgba(): string {
    const o = Math.round;
    const r = Math.random;
    const s = 255;
    return `rgba(${o(r() * s)}, ${o(r() * s)}, ${o(r() * s)}, 0.5)`;
  }
}
