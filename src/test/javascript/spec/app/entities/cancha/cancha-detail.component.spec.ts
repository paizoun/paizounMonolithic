import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PaizounTestModule } from '../../../test.module';
import { CanchaDetailComponent } from 'app/entities/cancha/cancha-detail.component';
import { Cancha } from 'app/shared/model/cancha.model';

describe('Component Tests', () => {
  describe('Cancha Management Detail Component', () => {
    let comp: CanchaDetailComponent;
    let fixture: ComponentFixture<CanchaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ cancha: new Cancha(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [CanchaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CanchaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CanchaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load cancha on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cancha).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
