import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoCancha, TipoCancha } from 'app/shared/model/tipo-cancha.model';
import { TipoCanchaService } from './tipo-cancha.service';
import { TipoCanchaComponent } from './tipo-cancha.component';
import { TipoCanchaDetailComponent } from './tipo-cancha-detail.component';
import { TipoCanchaUpdateComponent } from './tipo-cancha-update.component';

@Injectable({ providedIn: 'root' })
export class TipoCanchaResolve implements Resolve<ITipoCancha> {
  constructor(private service: TipoCanchaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoCancha> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoCancha: HttpResponse<TipoCancha>) => {
          if (tipoCancha.body) {
            return of(tipoCancha.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoCancha());
  }
}

export const tipoCanchaRoute: Routes = [
  {
    path: '',
    component: TipoCanchaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.tipoCancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoCanchaDetailComponent,
    resolve: {
      tipoCancha: TipoCanchaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.tipoCancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoCanchaUpdateComponent,
    resolve: {
      tipoCancha: TipoCanchaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.tipoCancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoCanchaUpdateComponent,
    resolve: {
      tipoCancha: TipoCanchaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.tipoCancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
