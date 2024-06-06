export interface ICongeStatistic {
  id: number;
  year: number;
  month: number;
  typesWithCounts: Map<string, number>;
}

export class CongeStatistic implements ICongeStatistic {
  constructor(
    public id: number,
    public year: number,
    public month: number,
    public typesWithCounts: Map<string, number>
  ) {
  }
}
