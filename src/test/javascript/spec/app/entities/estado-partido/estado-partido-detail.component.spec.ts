import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { EstadoPartidoDetailComponent } from 'app/entities/estado-partido/estado-partido-detail.component';
import { EstadoPartido } from 'app/shared/model/estado-partido.model';

describe('Component Tests', () => {
  describe('EstadoPartido Management Detail Component', () => {
    let comp: EstadoPartidoDetailComponent;
    let fixture: ComponentFixture<EstadoPartidoDetailComponent>;
    const route = ({ data: of({ estadoPartido: new EstadoPartido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [EstadoPartidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoPartidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoPartidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoPartido on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoPartido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
