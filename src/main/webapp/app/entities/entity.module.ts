import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cancha',
        loadChildren: () => import('./cancha/cancha.module').then(m => m.PaizounCanchaModule)
      },
      {
        path: 'tipo-cancha',
        loadChildren: () => import('./tipo-cancha/tipo-cancha.module').then(m => m.PaizounTipoCanchaModule)
      },
      {
        path: 'equipo',
        loadChildren: () => import('./equipo/equipo.module').then(m => m.PaizounEquipoModule)
      },
      {
        path: 'estado-partido',
        loadChildren: () => import('./estado-partido/estado-partido.module').then(m => m.PaizounEstadoPartidoModule)
      },
      {
        path: 'partido',
        loadChildren: () => import('./partido/partido.module').then(m => m.PaizounPartidoModule)
      },
      {
        path: 'invitacion-partido',
        loadChildren: () => import('./invitacion-partido/invitacion-partido.module').then(m => m.PaizounInvitacionPartidoModule)
      },
      {
        path: 'resultado-partido',
        loadChildren: () => import('./resultado-partido/resultado-partido.module').then(m => m.PaizounResultadoPartidoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PaizounEntityModule {}
