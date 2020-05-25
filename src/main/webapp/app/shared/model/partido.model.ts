import { Moment } from 'moment';
import { IInvitacionPartido } from 'app/shared/model/invitacion-partido.model';
import { IEstadoPartido } from 'app/shared/model/estado-partido.model';

export interface IPartido {
  id?: number;
  fechaHora?: Moment;
  finalizado?: boolean;
  invitacionPartido?: IInvitacionPartido;
  estadoPartido?: IEstadoPartido;
}

export class Partido implements IPartido {
  constructor(
    public id?: number,
    public fechaHora?: Moment,
    public finalizado?: boolean,
    public invitacionPartido?: IInvitacionPartido,
    public estadoPartido?: IEstadoPartido
  ) {
    this.finalizado = this.finalizado || false;
  }
}
