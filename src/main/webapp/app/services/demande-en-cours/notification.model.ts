export interface INotification {
  initiateur: string;
  type: string;
  collaborateur: string;
  taskName: string;
  processInstanceId: string;
  demande: any;
  dueDate: Date;
  createdAt: Date;
  taskId: string;
}

export class Notification implements INotification {
  constructor(
    public processInstanceId: string,
    public initiateur: string,
    public collaborateur: string,
    public taskId: string,
    public taskName: string,
    public demande: any,
    public type: string,
    public dueDate: Date,
    public createdAt: Date
  ) {
  }
}
