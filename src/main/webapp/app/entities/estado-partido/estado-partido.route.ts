import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoPartido, EstadoPartido } from 'app/shared/model/estado-partido.model';
import { EstadoPartidoService } from './estado-partido.service';
import { EstadoPartidoComponent } from './estado-partido.component';
import { EstadoPartidoDetailComponent } from './estado-partido-detail.component';
import { EstadoPartidoUpdateComponent } from './estado-partido-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoPartidoResolve implements Resolve<IEstadoPartido> {
  constructor(private service: EstadoPartidoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoPartido> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoPartido: HttpResponse<EstadoPartido>) => {
          if (estadoPartido.body) {
            return of(estadoPartido.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoPartido());
  }
}

export const estadoPartidoRoute: Routes = [
  {
    path: '',
    component: EstadoPartidoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.estadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoPartidoDetailComponent,
    resolve: {
      estadoPartido: EstadoPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.estadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoPartidoUpdateComponent,
    resolve: {
      estadoPartido: EstadoPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.estadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoPartidoUpdateComponent,
    resolve: {
      estadoPartido: EstadoPartidoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.estadoPartido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
