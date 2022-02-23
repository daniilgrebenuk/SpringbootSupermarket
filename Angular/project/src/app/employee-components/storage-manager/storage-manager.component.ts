import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {StorageService} from "../../service/company/storage.service";
import {Storage} from "../../model/storage";
import {ActivatedRoute, Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {Renderer} from "@angular/compiler-cli/ngcc/src/rendering/renderer";

@Component({
  selector: 'app-storage-manager',
  templateUrl: './storage-manager.component.html',
  styleUrls: ['./storage-manager.component.css']
})
export class StorageManagerComponent implements OnInit {

  public storages: Storage[] = [];
  public search:string = "";

  constructor(
    private storageService: StorageService,
    private router: Router,
    private elem: ElementRef,
    private renderer: Renderer2
  ) { }

  ngOnInit(): void {
    this.initStorage();
  }

  private initStorage(){
    this.storageService.getAllStorage().subscribe(
      resp=>{
        this.storages = resp;
        this.hideSpinnerAndShowAddButton();
      }
    )
  }

  addStorageOnClick() {
    let location = prompt("Write location");
    if (location) {
      this.storageService.addStorage(location).subscribe(
        resp => {
          console.log(resp);
          this.initStorage();
        }
      );
    }
  }

  clearSearch(){
    this.search = "";
  }

  filterStorage(): Storage[]{
    return this.storages.filter(s=>s.location.includes(this.search));
  }

  redirectToSupplyByStorageId(id: number) {
    this.router.navigate([`storage/${id}`]);
  }

  private hideSpinnerAndShowAddButton(){
    let spinner = document.getElementById("spinner")
    let storage = document.getElementById("storage-wrap")

    spinner?.remove();
    storage!.style.opacity = "1";
  }
}
