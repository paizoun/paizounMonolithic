import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvitacionPartido } from 'app/shared/model/invitacion-partido.model';

type EntityResponseType = HttpResponse<IInvitacionPartido>;
type EntityArrayResponseType = HttpResponse<IInvitacionPartido[]>;

@Injectable({ providedIn: 'root' })
export class InvitacionPartidoService {
  public resourceUrl = SERVER_API_URL + 'api/invitacion-partidos';

  constructor(protected http: HttpClient) {}

  create(invitacionPartido: IInvitacionPartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invitacionPartido);
    return this.http
      .post<IInvitacionPartido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invitacionPartido: IInvitacionPartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invitacionPartido);
    return this.http
      .put<IInvitacionPartido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvitacionPartido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvitacionPartido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invitacionPartido: IInvitacionPartido): IInvitacionPartido {
    const copy: IInvitacionPartido = Object.assign({}, invitacionPartido, {
      fechaHoraCreacion:
        invitacionPartido.fechaHoraCreacion && invitacionPartido.fechaHoraCreacion.isValid()
          ? invitacionPartido.fechaHoraCreacion.toJSON()
          : undefined,
      fechaHoraPartido:
        invitacionPartido.fechaHoraPartido && invitacionPartido.fechaHoraPartido.isValid()
          ? invitacionPartido.fechaHoraPartido.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaHoraCreacion = res.body.fechaHoraCreacion ? moment(res.body.fechaHoraCreacion) : undefined;
      res.body.fechaHoraPartido = res.body.fechaHoraPartido ? moment(res.body.fechaHoraPartido) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invitacionPartido: IInvitacionPartido) => {
        invitacionPartido.fechaHoraCreacion = invitacionPartido.fechaHoraCreacion ? moment(invitacionPartido.fechaHoraCreacion) : undefined;
        invitacionPartido.fechaHoraPartido = invitacionPartido.fechaHoraPartido ? moment(invitacionPartido.fechaHoraPartido) : undefined;
      });
    }
    return res;
  }
}
