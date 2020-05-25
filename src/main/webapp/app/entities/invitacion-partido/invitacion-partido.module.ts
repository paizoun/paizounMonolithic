import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { InvitacionPartidoComponent } from './invitacion-partido.component';
import { InvitacionPartidoDetailComponent } from './invitacion-partido-detail.component';
import { InvitacionPartidoUpdateComponent } from './invitacion-partido-update.component';
import { InvitacionPartidoDeleteDialogComponent } from './invitacion-partido-delete-dialog.component';
import { invitacionPartidoRoute } from './invitacion-partido.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(invitacionPartidoRoute)],
  declarations: [
    InvitacionPartidoComponent,
    InvitacionPartidoDetailComponent,
    InvitacionPartidoUpdateComponent,
    InvitacionPartidoDeleteDialogComponent
  ],
  entryComponents: [InvitacionPartidoDeleteDialogComponent]
})
export class PaizounInvitacionPartidoModule {}
