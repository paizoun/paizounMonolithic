import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartido } from 'app/shared/model/partido.model';

type EntityResponseType = HttpResponse<IPartido>;
type EntityArrayResponseType = HttpResponse<IPartido[]>;

@Injectable({ providedIn: 'root' })
export class PartidoService {
  public resourceUrl = SERVER_API_URL + 'api/partidos';

  constructor(protected http: HttpClient) {}

  create(partido: IPartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partido);
    return this.http
      .post<IPartido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partido: IPartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partido);
    return this.http
      .put<IPartido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partido: IPartido): IPartido {
    const copy: IPartido = Object.assign({}, partido, {
      fechaHora: partido.fechaHora && partido.fechaHora.isValid() ? partido.fechaHora.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaHora = res.body.fechaHora ? moment(res.body.fechaHora) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partido: IPartido) => {
        partido.fechaHora = partido.fechaHora ? moment(partido.fechaHora) : undefined;
      });
    }
    return res;
  }
}
