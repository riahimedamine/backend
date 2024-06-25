import { NgModule } from '@angular/core';
import { CongeStatisticComponent } from './conge-statistic/conge-statistic.component';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule } from '@angular/router';
import { StatisticRoute } from './statistic.route';


@NgModule({
  declarations: [
    CongeStatisticComponent
  ],
  exports: [
    CongeStatisticComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(StatisticRoute)
  ]
})
export class StatisticModule {
}
