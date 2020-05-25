import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultadoPartido, ResultadoPartido } from 'app/shared/model/resultado-partido.model';
import { ResultadoPartidoService } from './resultado-partido.service';
import { ResultadoPartidoComponent } from './resultado-partido.component';
import { ResultadoPartidoDetailComponent } from './resultado-partido-detail.component';
import { ResultadoPartidoUpdateComponent } from './resultado-partido-update.component';

@Injectable({ providedIn: 'root' })
export class ResultadoPartidoResolve implements Resolve<IResultadoPartido> {
  constructor(private service: ResultadoPartidoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultadoPartido> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultadoPartido: HttpResponse<ResultadoPartido>) => {
          if (resultadoPartido.body) {
            return of(resultadoPartido.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultadoPartido());
  }
}

export const resultadoPartidoRoute: Routes = [
  {
    path: '',
    component: ResultadoPartidoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.resultadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResultadoPartidoDetailComponent,
    resolve: {
      resultadoPartido: ResultadoPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.resultadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResultadoPartidoUpdateComponent,
    resolve: {
      resultadoPartido: ResultadoPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.resultadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResultadoPartidoUpdateComponent,
    resolve: {
      resultadoPartido: ResultadoPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.resultadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
