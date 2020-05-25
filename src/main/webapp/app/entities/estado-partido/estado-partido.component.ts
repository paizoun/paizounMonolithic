import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoPartido } from 'app/shared/model/estado-partido.model';
import { EstadoPartidoService } from './estado-partido.service';
import { EstadoPartidoDeleteDialogComponent } from './estado-partido-delete-dialog.component';

@Component({
  selector: 'jhi-estado-partido',
  templateUrl: './estado-partido.component.html'
})
export class EstadoPartidoComponent implements OnInit, OnDestroy {
  estadoPartidos?: IEstadoPartido[];
  eventSubscriber?: Subscription;

  constructor(
    protected estadoPartidoService: EstadoPartidoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.estadoPartidoService.query().subscribe((res: HttpResponse<IEstadoPartido[]>) => (this.estadoPartidos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEstadoPartidos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEstadoPartido): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEstadoPartidos(): void {
    this.eventSubscriber = this.eventManager.subscribe('estadoPartidoListModification', () => this.loadAll());
  }

  delete(estadoPartido: IEstadoPartido): void {
    const modalRef = this.modalService.open(EstadoPartidoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estadoPartido = estadoPartido;
  }
}
