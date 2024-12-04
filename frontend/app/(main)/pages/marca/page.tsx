/* eslint-disable @next/next/no-img-element */
'use client';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { FileUpload } from 'primereact/fileupload';
import { InputText } from 'primereact/inputtext';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import { classNames } from 'primereact/utils';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { Projeto } from '@/types';
import { MarcaService } from '@/service/MarcaService';

const Marca = () => {
    let novoObjetoMarca: Projeto.Marca = {
        id: 0,
        nome: ''
    };

    const [objetoMarcasList, setMarcasList] = useState<Projeto.Marca[]>([]);
    const [objetoMarcaDialog, setMarcaDialog] = useState(false);
    const [deleteObjetoMarcaDialog, setDeleteMarcaDialog] = useState(false);
    const [deleteObjetosMarcasDialog, setDeleteMarcasDialog] = useState(false);
    const [objetoMarca, setMarca] = useState<Projeto.Marca>(novoObjetoMarca);
    const [selectedObjetoMarcas, setSelectedMarcas] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState('');
    const toast = useRef<Toast>(null);
    const dt = useRef<DataTable<any>>(null);
    const marcaService = useMemo(() => new MarcaService(), []);

    useEffect(() => {
        if(objetoMarcasList.length == 0) {
            marcaService
            .listAll()
            .then((response) => {
                console.log(response.data);
                setMarcasList(response.data)
            }).catch((error) => {
                console.log(error);
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro!',
                    detail: 'Não foi possível listar as marcas' + error.data.message,
                    life: 3000
                });
            })
        }
    }, [marcaService, objetoMarcasList]);

    const openNew = () => {
        setMarca(novoObjetoMarca);
        setSubmitted(false);
        setMarcaDialog(true);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setMarcaDialog(false);
    };

    const hideDeleteProductDialog = () => {
        setDeleteMarcaDialog(false);
    };

    const hideDeleteProductsDialog = () => {
        setDeleteMarcasDialog(false);
    };

    const saveMarcaOrUpdate = () => {
        setSubmitted(true);

        if (!objetoMarca.id) {
            objetoMarca.nome.trim();
            marcaService
            .criarMarca(objetoMarca)
            .then((response) => {
                console.log(response.data);
                setMarcaDialog(false);
                setMarca(novoObjetoMarca);
                setMarcasList([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso!',
                    detail: 'Marca criada com sucesso!',
                    life: 3000
                });
            }).catch(error => {
                const errorMessage =
                error.response?.data?.message || // Se o backend retornar um JSON com a mensagem
                error.response?.data || // Caso a mensagem seja um texto simples
                "Não foi possível criar nova Marca."; // Fallback para mensagens desconhecidas
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro',
                    detail: errorMessage,
                    life: 3000
                });
            });
        } else {
            objetoMarca.nome.trim();
            console.log("---------------------")
            console.log("OBjeto id da Marca = " + objetoMarca.id)
            console.log("---------------------")
            marcaService.atualizarMarca(objetoMarca)
            .then((response) => {
                console.log(response.data);
                setMarcaDialog(false);
                setMarca(novoObjetoMarca);
                setMarcasList([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso!',
                    detail: 'Estado alterado com sucesso!',
                    life: 3000
                });
            }).catch(error => {
                const errorMessage =
                error.response?.data?.message || // Se o backend retornar um JSON com a mensagem
                error.response?.data || // Caso a mensagem seja um texto simples
                "Não foi possível atualizar a Marca."; // Fallback para mensagens desconhecidas
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro!',
                    detail: errorMessage,
                    life: 3000
                });
            });
        }
    };

    const editMarca = (product: Projeto.Marca) => {
        setMarca({ ...product });
        setMarcaDialog(true);
    };

    const confirmDeleteMarca = (product: Projeto.Marca) => {
        setMarca(product);
        setDeleteMarcaDialog(true);
    };

    const deleteMarca = () => {
        if (objetoMarca.id != undefined) {
            marcaService.eliminarMarca(objetoMarca.id)
            .then((response) => {
                setMarca(novoObjetoMarca);
                setDeleteMarcaDialog(false);
                setMarcasList([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso!',
                    detail: 'Marca deleteda com Sucesso!',
                    life: 3000 
                });
            }).catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro!',
                    detail: 'Erro ao deletar a Marca!',
                    life: 3000 
                });
            });
        }
    };

    // const findIndexById = (id: string) => {
    //     let index = -1;
    //     for (let i = 0; i < (products as any)?.length; i++) {
    //         if ((products as any)[i].id === id) {
    //             index = i;
    //             break;
    //         }
    //     }

    //     return index;
    // };

    // const createId = () => {
    //     let id = '';
    //     let chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    //     for (let i = 0; i < 5; i++) {
    //         id += chars.charAt(Math.floor(Math.random() * chars.length));
    //     }
    //     return id;
    // };

    const exportCSV = () => {
        dt.current?.exportCSV();
    };

    const confirmDeleteSelected = () => {
        setDeleteMarcasDialog(true);
    };

    const deleteSelectedMarcas = () => {
        // let _products = (products as any)?.filter((val: any) => !(selectedProducts as any)?.includes(val));
        // setMarcas(_products);
        // setDeleteProductsDialog(false);
        // setSelectedProducts(null);
        // toast.current?.show({
        //     severity: 'success',
        //     summary: 'Successful',
        //     detail: 'Products Deleted',
        //     life: 3000
        // });
    };

    // const onCategoryChange = (e: RadioButtonChangeEvent) => {
    //     let _product = { ...product };
    //     _product['category'] = e.value;
    //     setProduct(_product);
    // };

    const onInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, nome: string) => {
        const val = (e.target && e.target.value) || '';
        
        // Usamos a função de atualização de estado, que garante o uso do estado mais recente
        setMarca((prevMarca) => ({
            ...prevMarca,  // Copia todas as propriedades do estado anterior
            [nome]: val     // Atualiza apenas o campo específico com o novo valor
        }));
    };

    // const onInputNumberChange = (e: InputNumberValueChangeEvent, name: string) => {
    //     const val = e.value || 0;
    //     let _product = { ...product };
    //     _product[`${name}`] = val;

    //     setProduct(_product);
    // };

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <div className="my-2">
                    <Button label="New" icon="pi pi-plus" severity="success" className=" mr-2" onClick={openNew} />
                    <Button label="Delete" icon="pi pi-trash" severity="danger" onClick={confirmDeleteSelected} disabled={!selectedObjetoMarcas || !(selectedObjetoMarcas as any).length} />
                </div>
            </React.Fragment>
        );
    };

    const rightToolbarTemplate = () => {
        return (
            <React.Fragment>
                <FileUpload mode="basic" accept="image/*" maxFileSize={1000000} chooseLabel="Import" className="mr-2 inline-block" />
                <Button label="Export" icon="pi pi-upload" severity="help" onClick={exportCSV} />
            </React.Fragment>
        );
    };

    const codeBodyTemplate = (rowData: Projeto.Marca) => {
        return (
            <>
                <span className="p-column-title">Código Marca</span>
                {rowData.id}
            </>
        );
    };

    const nameBodyTemplate = (rowData: Projeto.Marca) => {
        return (
            <>
                <span className="p-column-title">Nome</span>
                {rowData.nome}
            </>
        );
    };

    // const imageBodyTemplate = (rowData: Projeto.Marca) => {
    //     return (
    //         <>
    //             <span className="p-column-title">Image</span>
    //             <img src={`/demo/images/product/${rowData.image}`} alt={rowData.image} className="shadow-2" width="100" />
    //         </>
    //     );
    // };

    const actionBodyTemplate = (rowData: Projeto.Marca) => {
        return (
            <>
                <Button icon="pi pi-pencil" rounded severity="success" className="mr-2" onClick={() => editMarca(rowData)} />
                <Button icon="pi pi-trash" rounded severity="warning" onClick={() => confirmDeleteMarca(rowData)} />
            </>
        );
    };

    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Manage Products</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.currentTarget.value)} placeholder="Search..." />
            </span>
        </div>
    );

    const marcaDialogFooter = (
        <>
            <Button label="Cancel" icon="pi pi-times" text onClick={hideDialog} />
            <Button label="Save" icon="pi pi-check" text onClick={saveMarcaOrUpdate} />
        </>
    );
    const deleteMarcaDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" text onClick={hideDeleteProductDialog} />
            <Button label="Yes" icon="pi pi-check" text onClick={deleteMarca} />
        </>
    );
    const deleteMarcasDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" text onClick={hideDeleteProductsDialog} />
            <Button label="Yes" icon="pi pi-check" text onClick={deleteSelectedMarcas} />
        </>
    );

    return (
        <div className="grid crud-demo">
            <div className="col-12">
                <div className="card">
                    <Toast ref={toast} />
                    <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

                    <DataTable
                        ref={dt}
                        value={objetoMarcasList}
                        selection={selectedObjetoMarcas}
                        onSelectionChange={(e) => setSelectedMarcas(e.value as any)}
                        dataKey="id"
                        paginator
                        rows={10}
                        rowsPerPageOptions={[5, 10, 25]}
                        className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                        globalFilter={globalFilter}
                        emptyMessage="No products found."
                        header={header}
                        responsiveLayout="scroll"
                    >
                        <Column selectionMode="multiple" headerStyle={{ width: '4rem' }}></Column>
                        <Column field="id" header="Código" sortable body={codeBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        <Column field="nome" header="Nome" sortable body={nameBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        {/* <Column header="Image" body={imageBodyTemplate}></Column> */}
                        {/* <Column field="price" header="Price" body={priceBodyTemplate} sortable></Column> */}
                        {/* <Column field="category" header="Category" sortable body={categoryBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column> */}
                        {/* <Column field="rating" header="Reviews" body={ratingBodyTemplate} sortable></Column> */}
                        {/* <Column field="inventoryStatus" header="Status" body={statusBodyTemplate} sortable headerStyle={{ minWidth: '10rem' }}></Column> */}
                        <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
                    </DataTable>

                    <Dialog visible={objetoMarcaDialog} style={{ width: '450px' }} header="Detalhe das Marcas" modal className="p-fluid" footer={marcaDialogFooter} onHide={hideDialog}>
                        {/* {product.image && <img src={`/demo/images/product/${product.image}`} alt={product.image} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />} */}
                        <div className="field">
                            <label htmlFor="name">Nome da Marca</label>
                            <InputText
                                id="name"
                                value={objetoMarca.nome}
                                onChange={(e) => onInputChange(e, 'nome')}
                                required
                                autoFocus
                                className={classNames({
                                    'p-invalid': submitted && !objetoMarca.nome
                                })}
                            />
                            {submitted && !objetoMarca.nome && <small className="p-invalid">Nome da Marca é Obrigatório.</small>}
                        </div>
                        {/* <div className="field">
                            <label htmlFor="description">Description</label>
                            <InputTextarea id="description" value={product.description} onChange={(e) => onInputChange(e, 'description')} required rows={3} cols={20} />
                        </div> */}
                    </Dialog>

                    <Dialog visible={deleteObjetoMarcaDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteMarcaDialogFooter} onHide={hideDeleteProductDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {objetoMarca && (
                                <span>
                                    Tem certeza que deseja deletar a Marca? <b>{objetoMarca.nome}</b>?
                                </span>
                            )}
                        </div>
                    </Dialog>

                    <Dialog visible={deleteObjetosMarcasDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteMarcasDialogFooter} onHide={hideDeleteProductsDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {objetoMarca && <span>Você tem certeza que deseja deletar toda(s) a(s) Marca(s) Selecionada(s)?</span>}
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
};

export default Marca;
