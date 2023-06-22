import React, { Component } from 'react'
import UserService from '../services/user-service'
import { Formik, Form, Field, ErrorMessage } from 'formik'
import AuthService from "../services/auth.service";

class Home extends Component{

    constructor(props) {
        super(props);
        const user = AuthService.getCurrentUser();
        this.state = {
            user: user,
            getPaymentErrMsg: undefined,
            sendMoneyErrMsg: undefined
        };
    }

    earnMoney(email){
        UserService.earnMoney(email)
        .then(
            (res) => {
                this.setState({user: res.data, getPaymentErrMsg: undefined})
            } ,
            (err) => {
                if (err.response.data){
                    this.setState({getPaymentErrMsg: err.response.data})
                }
            }
        )
    }


    onSubmit = (values) => {
        let emailTo = values.emailTo
        let amount = values.amount

        UserService.sendMoney(this.state.user.email, emailTo, amount)
        .then(
            (res) => {
                this.setState({user: res.data, })
            }
            ,
            (err) => {
                if (err.response.data){
                    this.setState({sendMoneyErrMsg: err.response.data})
                }
            }
        )
    }

    validate(values){
        let errors = {}

        if (!values.emailTo) {
            errors.emailTo = 'Enter a Username'
        }

        if (!values.amount || values.amount <= 0) {
            errors.amount = 'Enter a valid money amount'
        }
        return errors
    }


    render(){
        return (
            <div>
               <div className="left">
                    <h1>Your username - {this.state.user.username}</h1>
                    <h1>Your money - {this.state.user.money}</h1>
                    <button className="btn btn-success btn-block" onClick={() => this.earnMoney(this.state.user.email)}>GIVE ME MONEY</button>
                   {this.state.getPaymentErrMsg &&
                   <div className="form-group">
                       <div className="alert alert-danger" role="alert" style={{marginTop: "20px", marginRight: "25%"}}>
                           {this.state.getPaymentErrMsg}
                       </div>
                   </div>
                   }
               </div>
               
               <div className="right">
               <Formik
                        initialValues={{emailTo:'', amount:''}}
                        validateOnChange={false}
                        validateOnBlur={false}
                        onSubmit={this.onSubmit}
                        validate = {this.validate}
                        enableReinitialize={true}
                    >
                        {
                            () => (
                                <Form>
                                    <fieldset className="form-group">
                                        <h1 style={{marginLeft: '25%'}}>User email</h1>
                                        <Field className="form-control" type="text" name="emailTo" style={{width: '50%', margin:'auto'}}/>
                                    </fieldset>
                                    <ErrorMessage name="emailTo" component="div"
                                        className="alert alert-warning" style={{width: '50%', margin:'auto'}}/>

                                    <fieldset className="form-group">
                                        <h1 style={{marginLeft: '25%'}}>Amount</h1>
                                        <Field className="form-control" type="text" name="amount" style={{width: '50%', margin:'auto'}}/>
                                    </fieldset>
                                    <ErrorMessage name="amount" component="div"
                                        className="alert alert-warning" style={{width: '50%', margin:'auto'}}/>

                                    <button type="submit"  className="btn btn-primary btn-block" style={{marginLeft : "25%", marginTop: "20px"}}>Send money</button>
                                </Form>
                            )
                        }
                    </Formik>
                    {this.state.sendMoneyErrMsg && <div className='alert alert-warning' style={{width: '50%', margin:'auto'}} >{this.state.sendMoneyErrMsg}</div>}
               </div>
            </div>
        )
    }
}

export default Home
