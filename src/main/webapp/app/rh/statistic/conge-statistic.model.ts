export interface ICongeStatistic {
  id: number;
  year: number;
  month: number;
  totalDemandes: number;
  totalAcceptedDemandes: number;
  totalRefusedDemandes: number;
  typesWithCounts: Map<string, number>;
}

export class CongeStatistic implements ICongeStatistic {
  constructor(
    public id: number,
    public year: number,
    public month: number,
    public totalDemandes: number,
    public totalAcceptedDemandes: number,
    public totalRefusedDemandes: number,
    public typesWithCounts: Map<string, number>
  ) {
  }
}
