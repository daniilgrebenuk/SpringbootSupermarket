import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Supply} from "../../model/supply";
import {SupplyService} from "../../service/company/supply.service";
import {applySourceSpanToExpressionIfNeeded} from "@angular/compiler/src/output/output_ast";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-supply-manager',
  templateUrl: './supply-manager.component.html',
  styleUrls: ['./supply-manager.component.css']
})
export class SupplyManagerComponent implements OnInit {

  public search: string = "";
  public storageId: number = -1;
  public supplies: Supply[] = [];

  constructor(
    private rout: ActivatedRoute,
    private router: Router,
    private supplyService: SupplyService
  ) {
  }

  ngOnInit(): void {
    this.rout.params.subscribe(
      param => {
        this.storageId = param["idStorage"];
        this.initSupply(this.storageId);
      }
    ).unsubscribe();
  }


  private initSupply(id: number) {
    this.supplyService.getSupplyByStorageId(id).subscribe(
      resp => {
        this.supplies = resp;
        this.hideSpinnerAndShowAddButton();
      },
      error => console.log(error)
    )
  }

  filterSupply(): Supply[] {
    return this.supplies.filter(s => s.date.toString().includes(this.search))
  }

  clearSearch() {
    this.search = "";
  }

  addSupplyOnClick() {
    let date = prompt("Write date: (dd-mm-yyyy)");
    if (date) {
      date = date
        .split("-")
        .reverse()
        .reduce((s1, s2) => `${s1}-${s2}`, "")
        .substring(1);
      console.log(date);

      this.supplyService.addSupply(this.storageId, date).subscribe(
        resp => {
          console.log(resp);
          this.initSupply(this.storageId);
        }
      );
    }
  }

  private hideSpinnerAndShowAddButton(){
    let spinner = document.getElementById("spinner")
    let supply = document.getElementById("supply-wrap")

    spinner?.remove();
    supply!.style.opacity = "1";
  }

  redirectToSupplyById(id: number) {
    this.router.navigate([`supply/${id}`]);
  }
}
