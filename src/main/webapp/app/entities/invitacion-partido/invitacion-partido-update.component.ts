import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvitacionPartido, InvitacionPartido } from 'app/shared/model/invitacion-partido.model';
import { InvitacionPartidoService } from './invitacion-partido.service';
import { ICancha } from 'app/shared/model/cancha.model';
import { CanchaService } from 'app/entities/cancha/cancha.service';
import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoService } from 'app/entities/equipo/equipo.service';

type SelectableEntity = ICancha | IEquipo;

@Component({
  selector: 'jhi-invitacion-partido-update',
  templateUrl: './invitacion-partido-update.component.html'
})
export class InvitacionPartidoUpdateComponent implements OnInit {
  isSaving = false;
  canchas: ICancha[] = [];
  equipoas: IEquipo[] = [];
  equipobs: IEquipo[] = [];

  editForm = this.fb.group({
    id: [],
    fechaHoraCreacion: [null, [Validators.required]],
    fechaHoraPartido: [null, [Validators.required]],
    equipoAConfirmado: [null, [Validators.required]],
    equipoBConfirmado: [null, [Validators.required]],
    cancha: [],
    equipoA: [],
    equipoB: []
  });

  constructor(
    protected invitacionPartidoService: InvitacionPartidoService,
    protected canchaService: CanchaService,
    protected equipoService: EquipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invitacionPartido }) => {
      if (!invitacionPartido.id) {
        const today = moment().startOf('day');
        invitacionPartido.fechaHoraCreacion = today;
        invitacionPartido.fechaHoraPartido = today;
      }

      this.updateForm(invitacionPartido);

      this.canchaService
        .query({ filter: 'invitacionpartido-is-null' })
        .pipe(
          map((res: HttpResponse<ICancha[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICancha[]) => {
          if (!invitacionPartido.cancha || !invitacionPartido.cancha.id) {
            this.canchas = resBody;
          } else {
            this.canchaService
              .find(invitacionPartido.cancha.id)
              .pipe(
                map((subRes: HttpResponse<ICancha>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICancha[]) => (this.canchas = concatRes));
          }
        });

      this.equipoService
        .query({ filter: 'invitacionpartido-is-null' })
        .pipe(
          map((res: HttpResponse<IEquipo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEquipo[]) => {
          if (!invitacionPartido.equipoA || !invitacionPartido.equipoA.id) {
            this.equipoas = resBody;
          } else {
            this.equipoService
              .find(invitacionPartido.equipoA.id)
              .pipe(
                map((subRes: HttpResponse<IEquipo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEquipo[]) => (this.equipoas = concatRes));
          }
        });

      this.equipoService
        .query({ filter: 'invitacionpartido-is-null' })
        .pipe(
          map((res: HttpResponse<IEquipo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEquipo[]) => {
          if (!invitacionPartido.equipoB || !invitacionPartido.equipoB.id) {
            this.equipobs = resBody;
          } else {
            this.equipoService
              .find(invitacionPartido.equipoB.id)
              .pipe(
                map((subRes: HttpResponse<IEquipo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEquipo[]) => (this.equipobs = concatRes));
          }
        });
    });
  }

  updateForm(invitacionPartido: IInvitacionPartido): void {
    this.editForm.patchValue({
      id: invitacionPartido.id,
      fechaHoraCreacion: invitacionPartido.fechaHoraCreacion ? invitacionPartido.fechaHoraCreacion.format(DATE_TIME_FORMAT) : null,
      fechaHoraPartido: invitacionPartido.fechaHoraPartido ? invitacionPartido.fechaHoraPartido.format(DATE_TIME_FORMAT) : null,
      equipoAConfirmado: invitacionPartido.equipoAConfirmado,
      equipoBConfirmado: invitacionPartido.equipoBConfirmado,
      cancha: invitacionPartido.cancha,
      equipoA: invitacionPartido.equipoA,
      equipoB: invitacionPartido.equipoB
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invitacionPartido = this.createFromForm();
    if (invitacionPartido.id !== undefined) {
      this.subscribeToSaveResponse(this.invitacionPartidoService.update(invitacionPartido));
    } else {
      this.subscribeToSaveResponse(this.invitacionPartidoService.create(invitacionPartido));
    }
  }

  private createFromForm(): IInvitacionPartido {
    return {
      ...new InvitacionPartido(),
      id: this.editForm.get(['id'])!.value,
      fechaHoraCreacion: this.editForm.get(['fechaHoraCreacion'])!.value
        ? moment(this.editForm.get(['fechaHoraCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaHoraPartido: this.editForm.get(['fechaHoraPartido'])!.value
        ? moment(this.editForm.get(['fechaHoraPartido'])!.value, DATE_TIME_FORMAT)
        : undefined,
      equipoAConfirmado: this.editForm.get(['equipoAConfirmado'])!.value,
      equipoBConfirmado: this.editForm.get(['equipoBConfirmado'])!.value,
      cancha: this.editForm.get(['cancha'])!.value,
      equipoA: this.editForm.get(['equipoA'])!.value,
      equipoB: this.editForm.get(['equipoB'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvitacionPartido>>): void {
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
