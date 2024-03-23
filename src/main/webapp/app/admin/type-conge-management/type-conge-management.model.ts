export interface ITypeConge {
  id: number | null;
  libFr?: string | null;
  libAr?: string | null;
  code?: number | null;
  isDeleted?: boolean | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: Date | null;
}

export class TypeConge implements ITypeConge {
  constructor(
    public id: number | null,
    public libFr?: string | null,
    public libAr?: string | null,
    public code?: number | null,
    public isDeleted?: boolean | null,
    public createdBy?: string | null,
    public createdDate?: Date | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: Date | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}
