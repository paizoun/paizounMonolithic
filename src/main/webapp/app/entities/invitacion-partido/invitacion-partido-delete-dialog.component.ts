import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvitacionPartido } from 'app/shared/model/invitacion-partido.model';
import { InvitacionPartidoService } from './invitacion-partido.service';

@Component({
  templateUrl: './invitacion-partido-delete-dialog.component.html'
})
export class InvitacionPartidoDeleteDialogComponent {
  invitacionPartido?: IInvitacionPartido;

  constructor(
    protected invitacionPartidoService: InvitacionPartidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invitacionPartidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invitacionPartidoListModification');
      this.activeModal.close();
    });
  }
}
