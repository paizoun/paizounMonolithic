import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPartido, Partido } from 'app/shared/model/partido.model';
import { PartidoService } from './partido.service';
import { IInvitacionPartido } from 'app/shared/model/invitacion-partido.model';
import { InvitacionPartidoService } from 'app/entities/invitacion-partido/invitacion-partido.service';
import { IEstadoPartido } from 'app/shared/model/estado-partido.model';
import { EstadoPartidoService } from 'app/entities/estado-partido/estado-partido.service';

type SelectableEntity = IInvitacionPartido | IEstadoPartido;

@Component({
  selector: 'jhi-partido-update',
  templateUrl: './partido-update.component.html'
})
export class PartidoUpdateComponent implements OnInit {
  isSaving = false;
  invitacionpartidos: IInvitacionPartido[] = [];
  estadopartidos: IEstadoPartido[] = [];

  editForm = this.fb.group({
    id: [],
    fechaHora: [null, [Validators.required]],
    finalizado: [],
    invitacionPartido: [],
    estadoPartido: []
  });

  constructor(
    protected partidoService: PartidoService,
    protected invitacionPartidoService: InvitacionPartidoService,
    protected estadoPartidoService: EstadoPartidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partido }) => {
      if (!partido.id) {
        const today = moment().startOf('day');
        partido.fechaHora = today;
      }

      this.updateForm(partido);

      this.invitacionPartidoService
        .query({ filter: 'partido-is-null' })
        .pipe(
          map((res: HttpResponse<IInvitacionPartido[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInvitacionPartido[]) => {
          if (!partido.invitacionPartido || !partido.invitacionPartido.id) {
            this.invitacionpartidos = resBody;
          } else {
            this.invitacionPartidoService
              .find(partido.invitacionPartido.id)
              .pipe(
                map((subRes: HttpResponse<IInvitacionPartido>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInvitacionPartido[]) => (this.invitacionpartidos = concatRes));
          }
        });

      this.estadoPartidoService.query().subscribe((res: HttpResponse<IEstadoPartido[]>) => (this.estadopartidos = res.body || []));
    });
  }

  updateForm(partido: IPartido): void {
    this.editForm.patchValue({
      id: partido.id,
      fechaHora: partido.fechaHora ? partido.fechaHora.format(DATE_TIME_FORMAT) : null,
      finalizado: partido.finalizado,
      invitacionPartido: partido.invitacionPartido,
      estadoPartido: partido.estadoPartido
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partido = this.createFromForm();
    if (partido.id !== undefined) {
      this.subscribeToSaveResponse(this.partidoService.update(partido));
    } else {
      this.subscribeToSaveResponse(this.partidoService.create(partido));
    }
  }

  private createFromForm(): IPartido {
    return {
      ...new Partido(),
      id: this.editForm.get(['id'])!.value,
      fechaHora: this.editForm.get(['fechaHora'])!.value ? moment(this.editForm.get(['fechaHora'])!.value, DATE_TIME_FORMAT) : undefined,
      finalizado: this.editForm.get(['finalizado'])!.value,
      invitacionPartido: this.editForm.get(['invitacionPartido'])!.value,
      estadoPartido: this.editForm.get(['estadoPartido'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartido>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
