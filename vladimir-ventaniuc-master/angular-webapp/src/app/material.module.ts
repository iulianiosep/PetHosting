import { NgModule } from '@angular/core';

import {
  MatFormFieldModule,
  MatDialogModule,
  MatInputModule,
  MatButtonModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSortModule,
  MatTableModule,
  MatCardModule,
  MatGridListModule,
  MatCheckboxModule
 } from '@angular/material';

@NgModule({
  exports: [
    MatFormFieldModule,
   MatDialogModule,
   MatInputModule,
   MatButtonModule,
   MatPaginatorModule,
   MatProgressSpinnerModule,
   MatSortModule,
   MatTableModule,
   MatCardModule,
   MatGridListModule,
   MatCheckboxModule
  ]
})
export class MaterialModule { }
