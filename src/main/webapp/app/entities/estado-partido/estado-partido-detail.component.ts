import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoPartido } from 'app/shared/model/estado-partido.model';

@Component({
  selector: 'jhi-estado-partido-detail',
  templateUrl: './estado-partido-detail.component.html'
})
export class EstadoPartidoDetailComponent implements OnInit {
  estadoPartido: IEstadoPartido | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoPartido }) => (this.estadoPartido = estadoPartido));
  }

  previousState(): void {
    window.history.back();
  }
}
