import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoCancha } from 'app/shared/model/tipo-cancha.model';

@Component({
  selector: 'jhi-tipo-cancha-detail',
  templateUrl: './tipo-cancha-detail.component.html'
})
export class TipoCanchaDetailComponent implements OnInit {
  tipoCancha: ITipoCancha | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoCancha }) => (this.tipoCancha = tipoCancha));
  }

  previousState(): void {
    window.history.back();
  }
}
