import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { InvitacionPartidoUpdateComponent } from 'app/entities/invitacion-partido/invitacion-partido-update.component';
import { InvitacionPartidoService } from 'app/entities/invitacion-partido/invitacion-partido.service';
import { InvitacionPartido } from 'app/shared/model/invitacion-partido.model';

describe('Component Tests', () => {
  describe('InvitacionPartido Management Update Component', () => {
    let comp: InvitacionPartidoUpdateComponent;
    let fixture: ComponentFixture<InvitacionPartidoUpdateComponent>;
    let service: InvitacionPartidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [InvitacionPartidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InvitacionPartidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitacionPartidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitacionPartidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvitacionPartido(123);
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
        const entity = new InvitacionPartido();
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
