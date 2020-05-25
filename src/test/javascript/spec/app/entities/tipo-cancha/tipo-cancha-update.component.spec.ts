import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { TipoCanchaUpdateComponent } from 'app/entities/tipo-cancha/tipo-cancha-update.component';
import { TipoCanchaService } from 'app/entities/tipo-cancha/tipo-cancha.service';
import { TipoCancha } from 'app/shared/model/tipo-cancha.model';

describe('Component Tests', () => {
  describe('TipoCancha Management Update Component', () => {
    let comp: TipoCanchaUpdateComponent;
    let fixture: ComponentFixture<TipoCanchaUpdateComponent>;
    let service: TipoCanchaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TipoCanchaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoCanchaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoCanchaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoCanchaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoCancha(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoCancha();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
