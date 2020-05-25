import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvitacionPartido } from 'app/shared/model/invitacion-partido.model';

@Component({
  selector: 'jhi-invitacion-partido-detail',
  templateUrl: './invitacion-partido-detail.component.html'
})
export class InvitacionPartidoDetailComponent implements OnInit {
  invitacionPartido: IInvitacionPartido | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invitacionPartido }) => (this.invitacionPartido = invitacionPartido));
  }

  previousState(): void {
    window.history.back();
  }
}
