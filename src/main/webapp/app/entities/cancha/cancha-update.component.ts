import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICancha, Cancha } from 'app/shared/model/cancha.model';
import { CanchaService } from './cancha.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITipoCancha } from 'app/shared/model/tipo-cancha.model';
import { TipoCanchaService } from 'app/entities/tipo-cancha/tipo-cancha.service';

@Component({
  selector: 'jhi-cancha-update',
  templateUrl: './cancha-update.component.html'
})
export class CanchaUpdateComponent implements OnInit {
  isSaving = false;
  tipocanchas: ITipoCancha[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [],
    direccion: [null, [Validators.required]],
    ubicacion: [null, [Validators.required]],
    imagen: [],
    imagenContentType: [],
    fechaCreacion: [],
    estado: [null, [Validators.required]],
    tipoCancha: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected canchaService: CanchaService,
    protected tipoCanchaService: TipoCanchaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cancha }) => {
      if (!cancha.id) {
        const today = moment().startOf('day');
        cancha.fechaCreacion = today;
      }

      this.updateForm(cancha);

      this.tipoCanchaService.query().subscribe((res: HttpResponse<ITipoCancha[]>) => (this.tipocanchas = res.body || []));
    });
  }

  updateForm(cancha: ICancha): void {
    this.editForm.patchValue({
      id: cancha.id,
      nombre: cancha.nombre,
      descripcion: cancha.descripcion,
      direccion: cancha.direccion,
      ubicacion: cancha.ubicacion,
      imagen: cancha.imagen,
      imagenContentType: cancha.imagenContentType,
      fechaCreacion: cancha.fechaCreacion ? cancha.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      estado: cancha.estado,
      tipoCancha: cancha.tipoCancha
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('paizounApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cancha = this.createFromForm();
    if (cancha.id !== undefined) {
      this.subscribeToSaveResponse(this.canchaService.update(cancha));
    } else {
      this.subscribeToSaveResponse(this.canchaService.create(cancha));
    }
  }

  private createFromForm(): ICancha {
    return {
      ...new Cancha(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      ubicacion: this.editForm.get(['ubicacion'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estado: this.editForm.get(['estado'])!.value,
      tipoCancha: this.editForm.get(['tipoCancha'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICancha>>): void {
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

  trackById(index: number, item: ITipoCancha): any {
    return item.id;
  }
}
