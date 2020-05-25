import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoCancha } from 'app/shared/model/tipo-cancha.model';
import { TipoCanchaService } from './tipo-cancha.service';

@Component({
  templateUrl: './tipo-cancha-delete-dialog.component.html'
})
export class TipoCanchaDeleteDialogComponent {
  tipoCancha?: ITipoCancha;

  constructor(
    protected tipoCanchaService: TipoCanchaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoCanchaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoCanchaListModification');
      this.activeModal.close();
    });
  }
}
