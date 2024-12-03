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
import { EstadosService } from '@/service/EstadosService';

const Estados = () => {
    let novoObjetoEstados: Projeto.Estados = {
        id: 0,
        nome: '',
        sigla: ''
    };

    const [estados, setEstados] = useState<Projeto.Estados[]>([]);
    const [estadoDialog, setEstadoDialog] = useState(false);
    const [deleteEstadoDialog, setDeleteEstadoDialog] = useState(false);
    const [deleteEstadosDialog, setDeleteEstadosDialog] = useState(false);
    const [estado, setEstado] = useState<Projeto.Estados>(novoObjetoEstados);
    const [selectedEstados, setSelectedEstados] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState('');
    const toast = useRef<Toast>(null);
    const dt = useRef<DataTable<any>>(null);
    const estadosService = useMemo(() => new EstadosService(), []);

    useEffect(() => {
        if (estados.length == 0) {
            estadosService.listAll()
                .then((response) => {
                    console.log(response.data);
                    setEstados(response.data);
                }).catch((error) => {
                console.log(error);
            })
        }
    }, [estadosService, estados]);

    const openNew = () => {
        setEstado(novoObjetoEstados);
        setSubmitted(false);
        setEstadoDialog(true);
    };

    const hideEstadosDialog = () => {
        setSubmitted(false);
        setEstadoDialog(false);
    };

    const hideDeleteEstadoDialog = () => {
        setDeleteEstadoDialog(false);
    };

    const hideDeleteEstadosDialog = () => {
        setDeleteEstadosDialog(false);
    };

    const saveEstados = () => {
        setSubmitted(true);

        if (!estado.id) {
            estado.nome.trim();
            estado.sigla.trim().toUpperCase();
            estadosService.criar(estado)
            .then((response) => {
                console.log(response.data);
                setEstadoDialog(false);
                setEstado(novoObjetoEstados);
                setEstados([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: 'Estado salvo com sucesso!',
                    life: 3000
                });
            }).catch(error => {
                console.error("Erro ao salvar estado:", error.data.message);
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro',
                    detail: 'Não foi possível salvar o estado' + error.data.message,
                    life: 3000
                });
            });
        } else {
            estado.nome.trim();
            estado.sigla.trim().toUpperCase();
            estadosService.atulizar(estado)
            .then((response) => {
                console.log(response.data);
                setEstadoDialog(false);
                setEstado(novoObjetoEstados);
                setEstados([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso!',
                    detail: 'Estado alterado com sucesso!',
                    life: 3000
                });
            }).catch(error => {
                console.error("Erro ao salvar Estado:", error.data.message);
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro!',
                    detail: 'Não foi possível alterar o Estado' + error.data.message,
                    life: 3000
                });
            });
        }
    };

    const editEstados = (objetoEstados: Projeto.Estados) => {
        setEstado({ ...objetoEstados });
        setEstadoDialog(true);
    };

    const confirmDeleteEstado = (objetoEstados: Projeto.Estados) => {
        setEstado(objetoEstados);
        setDeleteEstadoDialog(true);
    };

    const deleteEstados = () => {
        if (estado.id != undefined) {
            estadosService.eliminar(estado.id)
            .then((response) => {
                setEstado(novoObjetoEstados);
                setDeleteEstadoDialog(false);
                setEstados([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso!',
                    detail: 'Estado deletedo com Sucesso!',
                    life: 3000 
                });
            }).catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro!',
                    detail: 'Erro ao deletar Estado!',
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
        setDeleteEstadosDialog(true);
    };

    const deleteSelectedEstados = () => {
        // let _products = (products as any)?.filter((val: any) => !(selectedProducts as any)?.includes(val));
        // setProducts(_products);
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
    //     let _estado = { ...estado };
    //     _estado['category'] = e.value;
    //     setEstado(_estado);
    // };

    const onInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, nome: string) => {
        const val = (e.target && e.target.value) || '';
        
        // Usamos a função de atualização de estado, que garante o uso do estado mais recente
        setEstado((prevEstado) => ({
            ...prevEstado,  // Copia todas as propriedades do estado anterior
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
                    <Button label="Novo" icon="pi pi-plus" severity="success" className=" mr-2" onClick={openNew} />
                    <Button label="Delete" icon="pi pi-trash" severity="danger" onClick={confirmDeleteSelected} disabled={!selectedEstados || !(selectedEstados as any).length} />
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

    const idBodyTemplate = (rowData: Projeto.Estados) => {
        return (
            <>
                <span className="p-column-title">Código Estados</span>
                {rowData.id}
            </>
        );
    };

    const nomeBodyTemplate = (rowData: Projeto.Estados) => {
        return (
            <>
                <span className="p-column-title">Nome Estados</span>
                {rowData.nome}
            </>
        );
    };

    const siglaBodyTemplate = (rowData: Projeto.Estados) => {
        return (
            <>
                <span className="p-column-title">Sigla Estados</span>
                {rowData.sigla}
            </>
        );
    };

    // const imageBodyTemplate = (rowData: Demo.Product) => {
    //     return (
    //         <>
    //             <span className="p-column-title">Image</span>
    //             <img src={`/demo/images/product/${rowData.image}`} alt={rowData.image} className="shadow-2" width="100" />
    //         </>
    //     );
    // };

    const actionBodyTemplate = (rowData: Projeto.Estados) => {
        return (
            <>
                <Button icon="pi pi-pencil" rounded severity="success" className="mr-2" onClick={() => editEstados(rowData)} />
                <Button icon="pi pi-trash" rounded severity="warning" onClick={() => confirmDeleteEstado(rowData)} />
            </>
        );
    };

    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Gerenciamento de Estados</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.currentTarget.value)} placeholder="Search..." />
            </span>
        </div>
    );

    const estadosDialogFooter = (
        <>
            <Button label="Cancelar" icon="pi pi-times" text onClick={hideEstadosDialog} />
            <Button label="Salvar" icon="pi pi-check" text onClick={saveEstados} />
        </>
    );
    const deleteEstadoDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" text onClick={hideDeleteEstadoDialog} />
            <Button label="Yes" icon="pi pi-check" text onClick={deleteEstados} />
        </>
    );
    const deleteEstadosDialogFooter = (
        <>
            <Button label="Não" icon="pi pi-times" text onClick={hideDeleteEstadosDialog} />
            <Button label="Sim" icon="pi pi-check" text onClick={deleteSelectedEstados} />
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
                        value={estados}
                        selection={selectedEstados}
                        onSelectionChange={(e) => setSelectedEstados(e.value as any)}
                        dataKey="id"
                        paginator
                        rows={10}
                        rowsPerPageOptions={[5, 10, 25]}
                        className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Mostrando {first} até  {last} de {totalRecords} Estados"
                        globalFilter={globalFilter}
                        emptyMessage="Nenhum Estados encontrado."
                        header={header}
                        responsiveLayout="scroll"
                    >
                        <Column selectionMode="multiple" headerStyle={{ width: '4rem' }}></Column>
                        <Column field="id" header="Código" sortable body={idBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        <Column field="nome" header="Nome" sortable body={nomeBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        <Column field="sigla" header="Sigla" sortable body={siglaBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        {/* <Column header="Image" body={imageBodyTemplate}></Column> */}
                        <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
                    </DataTable>

                    <Dialog visible={estadoDialog} style={{ width: '450px' }} header="Detalhe dos Estados" modal className="p-fluid" footer={estadosDialogFooter} onHide={hideEstadosDialog}>
                        {/* {estados.image && <img src={`/demo/images/product/${product.image}`} alt={product.image} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />} */}
                        <div className="field">
                            <label htmlFor="nome">Nome do Estado</label>
                            <InputText
                                id="nome"
                                value={estado.nome}
                                onChange={(e) => onInputChange(e, 'nome')}
                                required
                                autoFocus
                                className={classNames({
                                    'p-invalid': submitted && !estado.nome
                                })}
                            />
                            {submitted && !estado.nome && <small className="p-invalid">Nome é Obrigatório!</small>}
                        </div>
                        
                        <div className="field">
                            <label htmlFor="sigla">Sigla do Estado</label>
                            <InputText
                                id="sigla"
                                value={estado.sigla}
                                onChange={(e) => onInputChange(e, 'sigla')}
                                required
                                autoFocus
                                className={classNames({
                                    'p-invalid': submitted && !estado.sigla
                                })}
                            />
                            {submitted && !estado.sigla && <small className="p-invalid">Sigla é Obrigatório!</small>}
                        </div>
                    </Dialog>

                    <Dialog visible={deleteEstadoDialog} style={{ width: '450px' }} header="Confirmar" modal footer={deleteEstadoDialogFooter} onHide={hideDeleteEstadoDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {estado && (
                                <span>
                                    VOcê realmente deseja excluir o Estado? <b>{estado.nome}</b>?
                                </span>
                            )}
                        </div>
                    </Dialog>

                    <Dialog visible={deleteEstadosDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteEstadosDialogFooter} onHide={hideDeleteEstadosDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {estado && <span>Você tem certeza que deseja deletar os Estados selecionados?</span>}
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
};

export default Estados;
