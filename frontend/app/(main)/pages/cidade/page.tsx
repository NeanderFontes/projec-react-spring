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
import { CidadeService } from '@/service/CidadeService';
import { EstadosService } from '@/service/EstadosService';
import { Dropdown } from 'primereact/dropdown';
        

const Cidade = () => {
    let novoObjetoCidade: Projeto.Cidade = {
        id: 0,
        nome: '',
        estado: {id: 0, sigla: '', nome: ''},
    };

    const [cidades, setCidades] = useState<Projeto.Cidade[] | null>([]);
    const [cidadeDialog, setCidadeDialog] = useState(false);
    const [deleteCidadeDialog, setDeleteCidadeDialog] = useState(false);
    const [deleteCidadesDialog, setDeleteCidadesDialog] = useState(false);
    const [cidade, setCidade] = useState<Projeto.Cidade>(novoObjetoCidade);
    const [selectedCidades, setSelectedCidades] = useState<Projeto.Cidade[]>([]); 
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState('');
    const toast = useRef<Toast>(null);
    const dt = useRef<DataTable<any>>(null);
    const cidadeService = useMemo(() => new CidadeService(), []);
    const estadosService = useMemo(() => new EstadosService(), []);
    const [estados, setEstados] = useState<Projeto.Estados[]>();

    useEffect(() => {
        if (!cidades) {
            cidadeService
                .listAll()
                .then((response) => {
                    console.log(response.data);
                    setCidades(response.data); // Corrigido para setCidades
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }, [cidadeService, cidades]);

    useEffect(() => {
        cidadeService
            .listAll()
            .then((response) => setCidades(response.data))
            .catch((error) =>
                toast.current?.show({
                    severity: "info",
                    summary: "Erro!",
                    detail: "Erro ao fazer a listagem das Cidades!",
                    life: 3000,
                })
            );
    
        estadosService
            .listAll()
            .then((response) => setEstados(response.data))
            .catch((error) =>
                toast.current?.show({
                    severity: "info",
                    summary: "Erro!",
                    detail: "Erro ao fazer a listagem dos Estados!",
                    life: 3000,
                })
            );
    }, [cidadeService, estadosService]);
    

    const openNew = () => {
        setCidade(novoObjetoCidade);
        setSubmitted(false);
        setCidadeDialog(true);
    };

    const hideCidadesDialog = () => {
        setSubmitted(false);
        setCidadeDialog(false);
    };

    const hideDeleteCidadeDialog = () => {
        setDeleteCidadeDialog(false);
    };

    const hideDeleteCidadesDialog = () => {
        setDeleteCidadesDialog(false);
    };

    const saveCidades = () => {
        setSubmitted(true);

        if (!cidade.nome.trim() || !cidade.estado || !cidade.estado.id) {
            toast.current?.show({
                severity: "error",
                summary: "Erro!",
                detail: "Por favor, preencha todos os campos obrigatórios.",
                life: 3000,
            });
            return;
        }
    
        if (!cidade.id) {
            cidadeService
                .criar(cidade)
                .then((response) => {
                    setCidadeDialog(false);
                    setCidade(novoObjetoCidade);
                    toast.current?.show({
                        severity: "success",
                        summary: "Sucesso",
                        detail: "Cidade salva com sucesso!",
                        life: 3000,
                    });
                    cidadeService.listAll().then((res) => setCidades(res.data || []));
                })
                .catch((error) => {
                    const errorMessage =
                    error.response?.data?.message || // Se o backend retornar um JSON com a mensagem
                    error.response?.data || // Caso a mensagem seja um texto simples
                    "Não foi possível salvar a Cidade."; // Fallback para mensagens desconhecidas
                    toast.current?.show({
                        severity: "error",
                        summary: "Erro!",
                        detail: errorMessage,
                        life: 3000,
                    });
                });
        } else {
            cidadeService
                .atulizar(cidade)
                .then((response) => {
                    setCidadeDialog(false);
                    setCidade(novoObjetoCidade);
                    toast.current?.show({
                        severity: "success",
                        summary: "Sucesso",
                        detail: "Cidade atualizada com sucesso!",
                        life: 3000,
                    });
                    cidadeService.listAll().then((res) => setCidades(res.data || []));
                })
                .catch((error) => {
                    const errorMessage =
                    error.response?.data?.message || // Se o backend retornar um JSON com a mensagem
                    error.response?.data || // Caso a mensagem seja um texto simples
                    "Não foi possível salvar a Cidade."; // Fallback para mensagens desconhecidas
                    toast.current?.show({
                        severity: "error",
                        summary: "Erro",
                        detail: errorMessage,
                        life: 3000,
                    });
                });
        }
    };

    const editCidades = (objetoCidade: Projeto.Cidade) => {
        setCidade({ ...objetoCidade });
        setCidadeDialog(true);
    };

    const confirmDeleteCidade = (objetoCidade: Projeto.Cidade) => {
        setCidade(objetoCidade);
        setDeleteCidadeDialog(true);
    };

    const deleteCidades = () => {
        if (cidade.id) {
            cidadeService
                .eliminar(cidade.id)
                .then(() => {
                    setCidade(novoObjetoCidade);
                    setDeleteCidadeDialog(false);
                    toast.current?.show({
                        severity: 'success',
                        summary: 'Sucesso',
                        detail: 'Cidade excluída com sucesso!',
                        life: 3000
                    });
                    cidadeService.listAll().then((res) => setCidades(res.data || []));
                })
                .catch((error) => {
                    console.error('Erro ao excluir cidade:', error);
                    toast.current?.show({
                        severity: 'error',
                        summary: 'Erro',
                        detail: 'Não foi possível excluir a cidade.',
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
        setDeleteCidadesDialog(true);
    };

    const deleteSelectedCidades = () => {
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
        setCidade((prevCidade) => ({
            ...prevCidade,  // Copia todas as propriedades do estado anterior
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
                    <Button label="Delete" icon="pi pi-trash" severity="danger" onClick={confirmDeleteSelected} disabled={!selectedCidades || !(selectedCidades as any).length} />
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

    const idBodyTemplate = (rowData: Projeto.Cidade) => {
        return (
            <>
                <span className="p-column-title">Código Cidades</span>
                {rowData.id}
            </>
        );
    };

    const nomeBodyTemplate = (rowData: Projeto.Cidade) => {
        return (
            <>
                <span className="p-column-title">Nome da Cidade</span>
                {rowData.nome}
            </>
        );
    };

    const siglaBodyTemplate = (rowData: Projeto.Cidade) => {
        return (
            <>
                <span className="p-column-title">Estado</span>
                {rowData.estado
                    ? `${rowData.estado.sigla}` // Exibe apenas a sigla do estado
                    : "Sigla de Estado não definido"} 
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

    const actionBodyTemplate = (rowData: Projeto.Cidade) => {
        return (
            <>
                <Button icon="pi pi-pencil" rounded severity="success" className="mr-2" onClick={() => editCidades(rowData)} />
                <Button icon="pi pi-trash" rounded severity="warning" onClick={() => confirmDeleteCidade(rowData)} />
            </>
        );
    };

    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Gerenciamento de Cidades</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.currentTarget.value)} placeholder="Search..." />
            </span>
        </div>
    );

    const estadosDialogFooter = (
        <>
            <Button label="Cancelar" icon="pi pi-times" text onClick={hideCidadesDialog} />
            <Button label="Salvar" icon="pi pi-check" text onClick={saveCidades} />
        </>
    );
    const deleteEstadoDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" text onClick={hideDeleteCidadeDialog} />
            <Button label="Yes" icon="pi pi-check" text onClick={deleteCidades} />
        </>
    );
    const deleteEstadosDialogFooter = (
        <>
            <Button label="Não" icon="pi pi-times" text onClick={hideDeleteCidadesDialog} />
            <Button label="Sim" icon="pi pi-check" text onClick={deleteSelectedCidades} />
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
                        value={cidades || []} // Garante que sempre haverá um array, mesmo se cidades for null
                        selection={selectedCidades}
                        onSelectionChange={(e) => setSelectedCidades(e.value as any)}
                        dataKey="id"
                        paginator
                        rows={10}
                        rowsPerPageOptions={[5, 10, 25]}
                        className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Mostrando {first} até  {last} das {totalRecords} Cidades"
                        globalFilter={globalFilter}
                        emptyMessage="Nenhuma Cidade encontrada."
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

                    <Dialog visible={cidadeDialog} style={{ width: '450px' }} header="Detalhe das Cidades" modal className="p-fluid" footer={estadosDialogFooter} onHide={hideCidadesDialog}>
                        {/* {estados.image && <img src={`/demo/images/product/${product.image}`} alt={product.image} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />} */}
                        <div className="field">
                            <label htmlFor="nome">Nome da Cidade</label>
                            <InputText
                                id="nome"
                                value={cidade.nome}
                                onChange={(e) => onInputChange(e, 'nome')}
                                required
                                autoFocus
                                className={classNames({
                                    'p-invalid': submitted && !cidade.nome
                                })}
                            />
                            {submitted && !cidade.nome && <small className="p-invalid">Nome é Obrigatório!</small>}
                        </div>
                        
                        {<div className="field">
                            <label htmlFor="sigla">Nome do Estado</label>
                            <Dropdown
                            value={cidade.estado} // Objeto do estado
                            optionLabel="nome" // Campo exibido na lista
                            options={estados} // Lista de estados
                            onChange={(e) => setCidade((prevCidade) => ({ ...prevCidade, estado: e.value }))}
                            placeholder="Selecione um Estado"
                            className="w-full md:w-14rem" />
                            {submitted && !cidade.estado.sigla && <small className="p-invalid">Nome do Estado é Obrigatório!</small>}
                        </div>}
                    </Dialog>

                    <Dialog visible={deleteCidadeDialog} style={{ width: '450px' }} header="Confirmar" modal footer={deleteEstadoDialogFooter} onHide={hideDeleteCidadeDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {cidade && (
                                <span>
                                    Você realmente deseja excluir o Cidade? <b>{cidade.nome}</b>?
                                </span>
                            )}
                        </div>
                    </Dialog>

                    <Dialog visible={deleteCidadesDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteEstadosDialogFooter} onHide={hideDeleteCidadesDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {cidade && <span>Você tem certeza que deseja deletar os Cidades selecionados?</span>}
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
};

export default Cidade;
