import { IPartido } from 'app/shared/model/partido.model';

export interface IEstadoPartido {
  id?: number;
  estado?: string;
  partidos?: IPartido[];
}

export class EstadoPartido implements IEstadoPartido {
  constructor(public id?: number, public estado?: string, public partidos?: IPartido[]) {}
}
