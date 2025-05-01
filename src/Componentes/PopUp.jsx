import React from 'react';
import './PopUp.css';

const PopUp = ({ visivel, fechar, onConfirm, yesColor, noColor, children, podeConfirmar=true}) => {
    if (!visivel) return null;

    return (
        <div className='popup'>
            <div className='caixa'>
                <h3 style={{ color: 'black' }}>Atenção!</h3>
                {children}
                <div style={{ marginTop: '15px' }}>
                    <button className='btnpop' onClick={() => { if (podeConfirmar) { onConfirm(); fechar(); } }}
                        disabled={!podeConfirmar} style={{ backgroundColor: yesColor }}>Sim</button>
                    <button className='btnpop' onClick={fechar} style={{ marginLeft: '10px', backgroundColor: noColor }}>Não</button>
                </div>
            </div>
        </div>
    );
};

export default PopUp;