import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoPartido } from 'app/shared/model/estado-partido.model';
import { EstadoPartidoService } from './estado-partido.service';

@Component({
  templateUrl: './estado-partido-delete-dialog.component.html'
})
export class EstadoPartidoDeleteDialogComponent {
  estadoPartido?: IEstadoPartido;

  constructor(
    protected estadoPartidoService: EstadoPartidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoPartidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoPartidoListModification');
      this.activeModal.close();
    });
  }
}
