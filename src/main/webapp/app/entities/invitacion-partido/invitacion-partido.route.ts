import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvitacionPartido, InvitacionPartido } from 'app/shared/model/invitacion-partido.model';
import { InvitacionPartidoService } from './invitacion-partido.service';
import { InvitacionPartidoComponent } from './invitacion-partido.component';
import { InvitacionPartidoDetailComponent } from './invitacion-partido-detail.component';
import { InvitacionPartidoUpdateComponent } from './invitacion-partido-update.component';

@Injectable({ providedIn: 'root' })
export class InvitacionPartidoResolve implements Resolve<IInvitacionPartido> {
  constructor(private service: InvitacionPartidoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvitacionPartido> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invitacionPartido: HttpResponse<InvitacionPartido>) => {
          if (invitacionPartido.body) {
            return of(invitacionPartido.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvitacionPartido());
  }
}

export const invitacionPartidoRoute: Routes = [
  {
    path: '',
    component: InvitacionPartidoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'paizounApp.invitacionPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvitacionPartidoDetailComponent,
    resolve: {
      invitacionPartido: InvitacionPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.invitacionPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvitacionPartidoUpdateComponent,
    resolve: {
      invitacionPartido: InvitacionPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.invitacionPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvitacionPartidoUpdateComponent,
    resolve: {
      invitacionPartido: InvitacionPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.invitacionPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
