import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayment } from '../payment.model';
import { PaymentService } from '../service/payment.service';
import { PaymentCreateDialogComponent } from '../create/payment-create-dialog.component';
import { finalize, Observable } from 'rxjs';

@Component({
  selector: 'jhi-payment',
  templateUrl: './payment.component.html',
})
export class PaymentComponent implements OnInit {
  payments?: IPayment[];
  isLoading = false;
  pay: any;
  //payment: any;
  payment: any;
  paymentAmount: any;
  constructor(protected paymentService: PaymentService, protected modalService: NgbModal) {}
  ngOnInit(): void {
    this.pay = sessionStorage.getItem('payment');
    this.payment = JSON.parse(this.pay);
    this.loadAll();
    this.subscribeToSaveResponse(this.paymentService.getSendemail(this.payment));
  }

  // loadAll(): void {
  //   this.isLoading = true;

  //   this.payment = sessionStorage.getItem("payment");
  //   this.paymentAmount = JSON.parse(this.payment).paymentAmout;

  //   this.paymentService.getTransactionId().subscribe({
  //     next: (res: HttpResponse<string>) => {
  //       this.isLoading = false;
  //       this.payment = res.body ?? "";
  //       this.pay = JSON.parse(JSON.stringify(this.payment));
  //     },
  //     error: () => {
  //       this.isLoading = false;
  //     },
  //   });
  // }
  loadAll(): void {
    this.isLoading = true;
    this.pay = sessionStorage.getItem('payment');
    this.paymentAmount = JSON.parse(this.pay).paymentAmout;

    this.paymentService.query().subscribe({
      next: (res: HttpResponse<string>) => {
        this.isLoading = false;
        this.pay = res.body ?? '';

        this.payment = JSON.parse(JSON.stringify(this.pay));
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  trackId(_index: number, item: IPayment): number {
    return item.id!;
  }
  // send(): void {
  //   this.subscribeToSaveResponse(this.paymentService.getSendemail());
  // }

  delete(payment: IPayment): void {
    const modalRef = this.modalService.open(PaymentCreateDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.payment = payment;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
  onSaveFinalize(): void {
    throw new Error('Method not implemented.');
  }
  onSaveSuccess(): void {
    throw new Error('Method not implemented.');
  }
  onSaveError(): void {
    throw new Error('Method not implemented.');
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: (res: any) => {
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
    });
  }
}
