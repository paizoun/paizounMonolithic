import { ICancha } from 'app/shared/model/cancha.model';

export interface ITipoCancha {
  id?: number;
  nombreTipo?: string;
  cantidadJugadores?: number;
  canchas?: ICancha[];
}

export class TipoCancha implements ITipoCancha {
  constructor(public id?: number, public nombreTipo?: string, public cantidadJugadores?: number, public canchas?: ICancha[]) {}
}
