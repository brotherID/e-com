import React, { useState , useCallback , useMemo} from 'react'
// import Title from './Title';
// import Button from './Button';
// import Count from './Count';

const ParentComponent = () => {
    // const [age,setAge] = useState(20);
    // const [salary,setSalary] = useState(8000);

  
 
    // const incrementAge =  useCallback(
    //     () => {
    //         setAge(age+1);
    //     },
    //     [age],
    // )

    // const incrementSalary = useCallback(
    //     () => {
    //         setSalary(salary+1000);
    //     },
    //     [salary],
    // )



    const initialItems = new Array(29_999_999).fill(0).map((_, i) => {
        return {
          id: i,
          isSelected: i === 29_999_998,
        };
      });

    const [count, setCount] = useState(0);
    const [items] = useState(initialItems);


    const selectedItem = useMemo(
        () => 
            items.find((item) => item.id === count) ,  
        [items,count],
    );


    return (
        <div>
            {/* <Title></Title>
            <Count text="Age" count={age}></Count>
            <Button handleClick={incrementAge}>Increment Age</Button>
            <Count text="Salary" count={salary}></Count>
            <Button handleClick={incrementSalary}>Increment Salary</Button> */}
            <h1>Count: {count}</h1>
            <h1>Selected Item: {selectedItem.id}</h1>
            <button onClick={() => setCount(count + 1)}>
                Increment
            </button>
        </div>
    )
}

export default ParentComponent
