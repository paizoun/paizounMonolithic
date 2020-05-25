import { Moment } from 'moment';
import { ICancha } from 'app/shared/model/cancha.model';
import { IEquipo } from 'app/shared/model/equipo.model';

export interface IInvitacionPartido {
  id?: number;
  fechaHoraCreacion?: Moment;
  fechaHoraPartido?: Moment;
  equipoAConfirmado?: boolean;
  equipoBConfirmado?: boolean;
  cancha?: ICancha;
  equipoA?: IEquipo;
  equipoB?: IEquipo;
}

export class InvitacionPartido implements IInvitacionPartido {
  constructor(
    public id?: number,
    public fechaHoraCreacion?: Moment,
    public fechaHoraPartido?: Moment,
    public equipoAConfirmado?: boolean,
    public equipoBConfirmado?: boolean,
    public cancha?: ICancha,
    public equipoA?: IEquipo,
    public equipoB?: IEquipo
  ) {
    this.equipoAConfirmado = this.equipoAConfirmado || false;
    this.equipoBConfirmado = this.equipoBConfirmado || false;
  }
}
