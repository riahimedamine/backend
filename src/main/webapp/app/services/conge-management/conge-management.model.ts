export interface IDemandeConge {
  id: number | null;
  dateDebut?: Date;
  dateFin?: Date;
  vld?: number; // Assuming vld is a number (0 for en cours, 1 for validé, 2 for refusé)
  notes?: string | null;
  telephone?: number;
  address?: string;
  type?: string; // Assuming TypeConge is another interface or type you've defined
  user?: string;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}
export class DemandeConge implements IDemandeConge {
  constructor(
    public id: number | null,
    public dateDebut?: Date,
    public dateFin?: Date,
    public vld?: number,
    public notes?: string | null,
    public telephone?: number,
    public address?: string,
    public type?: string,
    public user?: string,
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}
