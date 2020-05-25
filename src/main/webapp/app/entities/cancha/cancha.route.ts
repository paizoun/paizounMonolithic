import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICancha, Cancha } from 'app/shared/model/cancha.model';
import { CanchaService } from './cancha.service';
import { CanchaComponent } from './cancha.component';
import { CanchaDetailComponent } from './cancha-detail.component';
import { CanchaUpdateComponent } from './cancha-update.component';

@Injectable({ providedIn: 'root' })
export class CanchaResolve implements Resolve<ICancha> {
  constructor(private service: CanchaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICancha> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cancha: HttpResponse<Cancha>) => {
          if (cancha.body) {
            return of(cancha.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cancha());
  }
}

export const canchaRoute: Routes = [
  {
    path: '',
    component: CanchaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'paizounApp.cancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CanchaDetailComponent,
    resolve: {
      cancha: CanchaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.cancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CanchaUpdateComponent,
    resolve: {
      cancha: CanchaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.cancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CanchaUpdateComponent,
    resolve: {
      cancha: CanchaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.cancha.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
