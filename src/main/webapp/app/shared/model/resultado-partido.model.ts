import { IPartido } from 'app/shared/model/partido.model';

export interface IResultadoPartido {
  id?: number;
  golesEqiopoA?: number;
  golesEqiopoB?: number;
  ganoEquipoA?: boolean;
  ganoEquipoB?: boolean;
  partido?: IPartido;
}

export class ResultadoPartido implements IResultadoPartido {
  constructor(
    public id?: number,
    public golesEqiopoA?: number,
    public golesEqiopoB?: number,
    public ganoEquipoA?: boolean,
    public ganoEquipoB?: boolean,
    public partido?: IPartido
  ) {
    this.ganoEquipoA = this.ganoEquipoA || false;
    this.ganoEquipoB = this.ganoEquipoB || false;
  }
}
