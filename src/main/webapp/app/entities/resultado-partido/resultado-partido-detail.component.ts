import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultadoPartido } from 'app/shared/model/resultado-partido.model';

@Component({
  selector: 'jhi-resultado-partido-detail',
  templateUrl: './resultado-partido-detail.component.html'
})
export class ResultadoPartidoDetailComponent implements OnInit {
  resultadoPartido: IResultadoPartido | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoPartido }) => (this.resultadoPartido = resultadoPartido));
  }

  previousState(): void {
    window.history.back();
  }
}
