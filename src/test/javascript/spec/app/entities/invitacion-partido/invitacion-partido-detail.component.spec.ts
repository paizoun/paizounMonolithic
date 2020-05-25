import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { InvitacionPartidoDetailComponent } from 'app/entities/invitacion-partido/invitacion-partido-detail.component';
import { InvitacionPartido } from 'app/shared/model/invitacion-partido.model';

describe('Component Tests', () => {
  describe('InvitacionPartido Management Detail Component', () => {
    let comp: InvitacionPartidoDetailComponent;
    let fixture: ComponentFixture<InvitacionPartidoDetailComponent>;
    const route = ({ data: of({ invitacionPartido: new InvitacionPartido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [InvitacionPartidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvitacionPartidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitacionPartidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invitacionPartido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invitacionPartido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
