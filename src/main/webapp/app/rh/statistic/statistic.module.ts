import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CongeStatisticComponent } from './conge-statistic/conge-statistic.component';


@NgModule({
  declarations: [
    CongeStatisticComponent
  ],
  exports: [
    CongeStatisticComponent
  ],
  imports: [
    CommonModule
  ]
})
export class StatisticModule {
}
