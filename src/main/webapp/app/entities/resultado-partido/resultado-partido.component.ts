import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultadoPartido } from 'app/shared/model/resultado-partido.model';
import { ResultadoPartidoService } from './resultado-partido.service';
import { ResultadoPartidoDeleteDialogComponent } from './resultado-partido-delete-dialog.component';

@Component({
  selector: 'jhi-resultado-partido',
  templateUrl: './resultado-partido.component.html'
})
export class ResultadoPartidoComponent implements OnInit, OnDestroy {
  resultadoPartidos?: IResultadoPartido[];
  eventSubscriber?: Subscription;

  constructor(
    protected resultadoPartidoService: ResultadoPartidoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.resultadoPartidoService.query().subscribe((res: HttpResponse<IResultadoPartido[]>) => (this.resultadoPartidos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInResultadoPartidos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IResultadoPartido): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInResultadoPartidos(): void {
    this.eventSubscriber = this.eventManager.subscribe('resultadoPartidoListModification', () => this.loadAll());
  }

  delete(resultadoPartido: IResultadoPartido): void {
    const modalRef = this.modalService.open(ResultadoPartidoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultadoPartido = resultadoPartido;
  }
}
