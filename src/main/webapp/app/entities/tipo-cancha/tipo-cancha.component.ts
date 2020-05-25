import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoCancha } from 'app/shared/model/tipo-cancha.model';
import { TipoCanchaService } from './tipo-cancha.service';
import { TipoCanchaDeleteDialogComponent } from './tipo-cancha-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-cancha',
  templateUrl: './tipo-cancha.component.html'
})
export class TipoCanchaComponent implements OnInit, OnDestroy {
  tipoCanchas?: ITipoCancha[];
  eventSubscriber?: Subscription;

  constructor(protected tipoCanchaService: TipoCanchaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tipoCanchaService.query().subscribe((res: HttpResponse<ITipoCancha[]>) => (this.tipoCanchas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoCanchas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoCancha): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoCanchas(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoCanchaListModification', () => this.loadAll());
  }

  delete(tipoCancha: ITipoCancha): void {
    const modalRef = this.modalService.open(TipoCanchaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoCancha = tipoCancha;
  }
}
