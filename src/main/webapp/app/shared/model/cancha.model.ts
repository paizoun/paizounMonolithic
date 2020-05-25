import { Moment } from 'moment';
import { ITipoCancha } from 'app/shared/model/tipo-cancha.model';

export interface ICancha {
  id?: number;
  nombre?: string;
  descripcion?: string;
  direccion?: string;
  ubicacion?: string;
  imagenContentType?: string;
  imagen?: any;
  fechaCreacion?: Moment;
  estado?: boolean;
  tipoCancha?: ITipoCancha;
}

export class Cancha implements ICancha {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public direccion?: string,
    public ubicacion?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public fechaCreacion?: Moment,
    public estado?: boolean,
    public tipoCancha?: ITipoCancha
  ) {
    this.estado = this.estado || false;
  }
}
