export interface ISolde {
  id: number | null;
  solde?: number | null;
  year?: number | null;
  user?: string | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: Date | null;
}

export class Solde implements ISolde {
  constructor(
    public id: number | null,
    public solde?: number | null,
    public year?: number | null,
    public user?: string | null,
    public createdBy?: string | null,
    public createdDate?: Date | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: Date | null
  ) {}
}
