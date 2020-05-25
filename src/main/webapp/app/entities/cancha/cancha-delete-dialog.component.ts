import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICancha } from 'app/shared/model/cancha.model';
import { CanchaService } from './cancha.service';

@Component({
  templateUrl: './cancha-delete-dialog.component.html'
})
export class CanchaDeleteDialogComponent {
  cancha?: ICancha;

  constructor(protected canchaService: CanchaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.canchaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('canchaListModification');
      this.activeModal.close();
    });
  }
}
