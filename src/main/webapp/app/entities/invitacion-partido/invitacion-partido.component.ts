import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvitacionPartido } from 'app/shared/model/invitacion-partido.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InvitacionPartidoService } from './invitacion-partido.service';
import { InvitacionPartidoDeleteDialogComponent } from './invitacion-partido-delete-dialog.component';

@Component({
  selector: 'jhi-invitacion-partido',
  templateUrl: './invitacion-partido.component.html'
})
export class InvitacionPartidoComponent implements OnInit, OnDestroy {
  invitacionPartidos?: IInvitacionPartido[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected invitacionPartidoService: InvitacionPartidoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.invitacionPartidoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IInvitacionPartido[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInInvitacionPartidos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvitacionPartido): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvitacionPartidos(): void {
    this.eventSubscriber = this.eventManager.subscribe('invitacionPartidoListModification', () => this.loadPage());
  }

  delete(invitacionPartido: IInvitacionPartido): void {
    const modalRef = this.modalService.open(InvitacionPartidoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invitacionPartido = invitacionPartido;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IInvitacionPartido[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/invitacion-partido'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.invitacionPartidos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
