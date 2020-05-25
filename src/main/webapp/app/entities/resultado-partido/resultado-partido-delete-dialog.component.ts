import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultadoPartido } from 'app/shared/model/resultado-partido.model';
import { ResultadoPartidoService } from './resultado-partido.service';

@Component({
  templateUrl: './resultado-partido-delete-dialog.component.html'
})
export class ResultadoPartidoDeleteDialogComponent {
  resultadoPartido?: IResultadoPartido;

  constructor(
    protected resultadoPartidoService: ResultadoPartidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultadoPartidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultadoPartidoListModification');
      this.activeModal.close();
    });
  }
}
