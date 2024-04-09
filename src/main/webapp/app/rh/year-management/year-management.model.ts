export interface IYear {
  id: number | null;

  year: number | null;
}

export class Year implements IYear {
  constructor(public id: number | null, public year: number | null) {}
}
