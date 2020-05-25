import { Moment } from 'moment';

export interface IEquipo {
  id?: number;
  nombre?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  fechaCreacion?: Moment;
}

export class Equipo implements IEquipo {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public fechaCreacion?: Moment
  ) {}
}
